package org.fkjava.oa.storage.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.fkjava.oa.storage.domain.FileInfo;
import org.fkjava.oa.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

// /storage/file
@Controller
@RequestMapping("/storage/file")
public class FileController {

	@Autowired
	private FileService fileService;

	// 默认第0页，因为Spring Data里面第一页为0开始的
	@GetMapping
	public ModelAndView index(//
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber, //
			@RequestParam(value = "name", required = false) String name, //
			@RequestParam(value = "orderBy", defaultValue = "name") String orderByProperty) {

		// /项目名称/模块名称/功能名称/操作名称
		// /storage/shared/file/index
		// /WEB-INF/views/shared/file/index.jsp
		ModelAndView mav = new ModelAndView("storage/file/index");

		Page<FileInfo> page = this.fileService.find(name, orderByProperty, pageNumber);
		mav.addObject("page", page);

		return mav;
	}

	@PostMapping
	public ModelAndView upload(@RequestParam("file") MultipartFile file) throws IOException {
		ModelAndView mav = new ModelAndView("redirect:/storage/file");

		String name = file.getOriginalFilename();
		String contentType = file.getContentType();
		long contentLength = file.getSize();
		try (InputStream content = file.getInputStream();) {
			// 调用业务逻辑层的代码
			this.fileService.save(name, contentType, contentLength, content);
		}
		return mav;
	}

	// 返回String，通过ViewResolver找视图
	// 返回View，本身就已经视图
	// 返回ModelAndView，可以直接是视图，也可以通过ViewResolver找视图
	// 返回ResponseEntity，自定义响应头、响应体（同步下载返回byte[]、异步下载返回StreamingResponseBody）
	// 文件下载
	@GetMapping("{id}")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable("id") String id)
			throws UnsupportedEncodingException {
		// 1.获取文件信息
		FileInfo info = this.fileService.getFileInfoById(id);

		if (info != null) {
			return this.sendFile(info);
		} else {
			return this.send404();
		}
//		StreamingResponseBody body = new StreamingResponseBody() {
//			@Override
//			public void writeTo(OutputStream outputStream) throws IOException {
//			}
//		};
	}

	private ResponseEntity<StreamingResponseBody> sendFile(FileInfo info) throws UnsupportedEncodingException {

		BodyBuilder builder;
		StreamingResponseBody body;

		File file = this.fileService.getFile(info);
		if (file.exists()) {
			builder = ResponseEntity.ok();// 200
			builder.contentType(MediaType.valueOf(info.getContentType()));
			builder.contentLength(info.getContentLength());
			String name = info.getName();
			name = URLEncoder.encode(name, "UTF-8");
			builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + name);

			body = (out) -> {
				Path source = file.toPath();
				Files.copy(source, out);
			};
		} else {
			return this.send404();
		}
		ResponseEntity<StreamingResponseBody> entity = builder.body(body);
		return entity;
	}

	private ResponseEntity<StreamingResponseBody> send404() throws UnsupportedEncodingException {
		BodyBuilder builder;
		StreamingResponseBody body;

		builder = ResponseEntity.status(404);// 404表示资源没有找到
		builder.contentType(MediaType.valueOf("text/html; charset=UTF-8"));

		String content = "文件未找到";
		byte[] data = content.getBytes("UTF-8");

		builder.contentLength(data.length);

		body = (out) -> {
			out.write(data);
		};
		ResponseEntity<StreamingResponseBody> entity = builder.body(body);
		return entity;
	}

	// 处理DELETE请求
	@DeleteMapping("{id}")
	@ResponseBody // 直接把“ok”作为字符串返回
	public String delete(@PathVariable("id") String id) {
		this.fileService.delete(id);
		return "ok";
	}

	// ============================================
	@PostMapping("wangEditor")
	@ResponseBody
	public WangEditorResult wangEditor(@RequestParam("file") MultipartFile file) {
		String name = file.getOriginalFilename();
		String contentType = file.getContentType();
		long contentLength = file.getSize();

		WangEditorResult result = new WangEditorResult();
		try (InputStream content = file.getInputStream();) {
			// 调用业务逻辑层的代码
			String id = this.fileService.save(name, contentType, contentLength, content);
			result.setErrno(0);
			// 拼接下载的路径给wangEditor
			result.getData().add("/storage/file/" + id);
		} catch (IOException ex) {
			result.setErrno(1);
		}
		return result;
	}

	public static class WangEditorResult {
		private int errno;
		private List<String> data = new LinkedList<>();

		public int getErrno() {
			return errno;
		}

		public void setErrno(int errno) {
			this.errno = errno;
		}

		public List<String> getData() {
			return data;
		}

		public void setData(List<String> data) {
			this.data = data;
		}
	}
}
