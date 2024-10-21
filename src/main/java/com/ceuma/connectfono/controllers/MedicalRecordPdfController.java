package com.ceuma.connectfono.controllers;

import com.aspose.pdf.*;
import com.aspose.pdf.operators.Do;
import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.models.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.io.File;


@Getter
@Setter
@AllArgsConstructor

public class MedicalRecordPdfController {
    //private MedicalRecord medicalRecord;

    public String generatePdf(MedicalRecordDTO medicalRecord) {
        Document doc = new Document();

        Page page = doc.getPages().add();
        page.setPageSize(PageSize.getA4().getWidth(), PageSize.getA4().getHeight());

//        TextFragment title = new TextFragment(medicalRecord.getMedicalRecord().getTitle());
//        title.setMargin(new MarginInfo(20, 20, 0, 0));
//        title.setPosition(new Position(20, 20));
        //title.setText(medicalRecord.getMedicalRecord().getTitle());
        //title.setText("teste");

        // Add Header

        TextFragment copyRight = new TextFragment("Clínica de FonoAudiologia do CEUMA");

        page.getParagraphs().add(generateTitle(medicalRecord.getMedicalRecord().getTitle()));
        page.getParagraphs().add(generateTopic("Dados do Paciente"));
        page.getParagraphs().add(generateMediumText("Nome"));
        page.getParagraphs().add(generateSmallText(medicalRecord.getMedicalRecord().getPatient().getName()));
        page.getParagraphs().add(generateMediumText("Cpf"));
        page.getParagraphs().add(generateSmallText(medicalRecord.getMedicalRecord().getPatient().getCpf()));
        page.getParagraphs().add(generateMediumText("Email"));
        page.getParagraphs().add(generateSmallText(medicalRecord.getMedicalRecord().getPatient().getEmail()));

        File fileName = new File("medicalRecordsPdf/" + medicalRecord.getMedicalRecord().getTitle() + ".pdf");

        //doc.save("prontuário: " + medicalRecord.getMedicalRecord().getTitle() + ".pdf");
        System.out.println("salvou o documento");
        doc.save(fileName.getAbsolutePath());
        System.out.println(fileName.getAbsolutePath());
        return fileName.getAbsolutePath();
    }

    public TextFragment generateTitle(String text){
        TextFragment header = new TextFragment(text.toUpperCase());
        header.getTextState().setFont(FontRepository.findFont("Arial-Bold"));
        header.getTextState().setFontSize(24);
        header.setHorizontalAlignment (HorizontalAlignment.Center);
        header.setPosition(new Position(130, 720));
        header.setMargin(new MarginInfo(0, 20, 0 ,0));
        return header;
    }

    public TextFragment generateTopic(String text){
        TextFragment topic = new TextFragment(text);
        topic.getTextState().setFont(FontRepository.findFont("Arial-Bold"));
        topic.getTextState().setFontSize(20);
        topic.setHorizontalAlignment (HorizontalAlignment.Left);
        topic.setMargin(new MarginInfo(0, 20, 0, 0));

        //patientName.setPosition(new Position(130, 820));
       return topic;
    }

    public TextFragment generateSmallText(String text){
        TextFragment smallText = new TextFragment(text);
        smallText.getTextState().setFont(FontRepository.findFont("Arial"));
        smallText.getTextState().setFontSize(14);
        smallText.setHorizontalAlignment (HorizontalAlignment.Left);
        smallText.setMargin(new MarginInfo(0, 2, 0, 0));
        return smallText;
    }
    public TextFragment generateMediumText(String text){
        TextFragment smallText = new TextFragment(text);
        smallText.getTextState().setFont(FontRepository.findFont("Arial-Bold"));
        smallText.getTextState().setFontSize(16);
        smallText.setHorizontalAlignment (HorizontalAlignment.Left);
        smallText.setMargin(new MarginInfo(0, 2, 0, 0));
        return smallText;
    }


}
