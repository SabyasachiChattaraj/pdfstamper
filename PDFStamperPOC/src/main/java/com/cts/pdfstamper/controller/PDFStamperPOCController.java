package com.cts.pdfstamper.controller;

import java.io.OutputStream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.cts.pdfstamper.util.PDFStamperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;

@Controller
@RequestMapping("VIEW")
public class PDFStamperPOCController {

	Log _log = LogFactoryUtil.getLog(PDFStamperPOCController.class);

	@RenderMapping
	public String handleRenderRequest(RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {

		return "view";

	}

	@ResourceMapping("downloadPDF")
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		OutputStream outStream = resourceResponse.getPortletOutputStream();
		byte[] pdfBytes = PDFStamperUtil.stampPDF("C:\\Users\\Sabyasachi\\Downloads\\552449.pdf", "1000101");
		System.out.println("pdfBytes :" + pdfBytes);
		resourceResponse.setContentType("application/pdf");
		resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL, "max-age=3600, must-revalidate");
		outStream.write(pdfBytes);
		outStream.flush();
		outStream.close();
	}

}
