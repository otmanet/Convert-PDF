package com.example.ConvertFilePdfToWord.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.example.ConvertFilePdfToWord.Model.fileDetails;

import com.example.ConvertFilePdfToWord.Service.StorageService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import net.bytebuddy.asm.Advice.Return;

@Controller
public class ConvertController {

    @Autowired
    StorageService storageService;
    /*
     * @Autowired
     * private ConvertFile convertFile;
     * 
     * private final String PDF_FORMAT = "pdf";
     */

    @GetMapping("/")
    public String indexFiles(Model model) {
        List<fileDetails> fileDetails = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String dir = path.toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ConvertController.class, "getFile",
                            path.getFileName().toString())
                    .build()
                    .toString();
            return new fileDetails(filename, url, dir);
        }).collect(Collectors.toList());
        model.addAttribute("files", fileDetails);
        return "index";
    }

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {

        try {
            storageService.save(file);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/files/{name:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String name) {
        Resource file = storageService.load(name);
        return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(value = "/deleteFile/{name:.+}")
    public String postMethodName(@PathVariable String name) throws IOException {

        File file = storageService.load(name).getFile();

        file.delete();
        return "redirect:/";
    }

    @RequestMapping(value = "/Convert/{absolutePath}")
    public void ConvertWord(@PathVariable String absolutePath, Model model,
            HttpServletResponse response)
            throws IOException {

        File file = storageService.load(absolutePath).getFile();
        // File f = new File(file.getAbsolutePath());
        PDDocument pdDocument = PDDocument.load(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String txt = pdfTextStripper.getText(pdDocument);
        pdDocument.close();
        // Docu
        model.addAttribute("txt", txt);

        List<String> lines = Arrays.asList(txt);
        InputStream is = new ByteArrayInputStream(lines.stream().collect(Collectors.joining("\n")).getBytes());
        IOUtils.copy(is, response.getOutputStream());
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=\"ConvertSuccess.txt\"");
        response.flushBuffer();
        // return "Text";
        // return "Text";
        // InputStreamResource resource = new InputStreamResource(new
        // FileInputStream(document.toString()));

        // org.springframework.http.HttpHeaders headers = new
        // org.springframework.http.HttpHeaders();
        // headers.add("content-disposition", "inline;name=" + document);

        // return ResponseEntity.ok()
        // .headers(headers)
        // .contentLength(file.length())
        // .contentType(MediaType.parseMediaType("application/doc"))
        // .body(document);
    }

}
