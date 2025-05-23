package com.ceuma.connectfono.controllers;

import com.aspose.pdf.*;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.models.Questions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor

public class MedicalRecordPdfController {
    //private MedicalRecord medicalRecord;

    public String generatePdf(MedicalRecord medicalRecord) {
        Document doc = new Document();
        Patient patient = medicalRecord.getPatient();
        List<Questions> questionsList = medicalRecord.getMedicalHistory().getQuestions();

        Page page = doc.getPages().add();
        page.setPageSize(PageSize.getA4().getWidth(), PageSize.getA4().getHeight());


        //TextFragment copyRight = new TextFragment("Clínica de FonoAudiologia do CEUMA");

        page.getParagraphs().add(generateTitle(medicalRecord.getTitle()));
        page.getParagraphs().add(generateMediumText("data de expedição", aligment.Center));
        page.getParagraphs().add(generateSmallText(
                medicalRecord.getDate().toString(), aligment.Center));

        page.getParagraphs().add(generateTopic("Dados do Paciente"));

        page.getParagraphs().add(generateMediumText("Nome"));
        page.getParagraphs().add(generateSmallText(patient.getName(),aligment.Left));
        page.getParagraphs().add(generateMediumText("Cpf"));
        page.getParagraphs().add(generateSmallText(patient.getCpf(),aligment.Left));
        page.getParagraphs().add(generateMediumText("Email"));
        page.getParagraphs().add(generateSmallText(patient.getEmail(),aligment.Left));
        page.getParagraphs().add(generateMediumText("Telefone"));
        page.getParagraphs().add(generateSmallText(patient.getPhone_number(),aligment.Left));

        page.getParagraphs().add(generateTopic("Motivo"));
        page.getParagraphs().add(generateSmallText(medicalRecord.getMotive()));

        page.getParagraphs().add(generateTopic("Diagnóstico"));
        page.getParagraphs().add(generateSmallText(medicalRecord.getDiagnosis(),aligment.Left));

        page.getParagraphs().add(generateTopic("Observações"));
        page.getParagraphs().add(generateSmallText(medicalRecord.getObservations(),aligment.Left));



        page.getParagraphs().add(generateTopic("Perguntas"));
        for (Questions question : questionsList) {
            page.getParagraphs().add(generateMediumText(question.getTitle()));
            page.getParagraphs().add(generateSmallText(question.getAnswer(),aligment.Left));
        }





        File fileName = new File("medicalRecordsPdf/" + medicalRecord.getTitle() + ".pdf");

        //doc.save("prontuário: " + medicalRecord.getMedicalRecord().getTitle() + ".pdf");
        System.out.println("salvou o documento");
        doc.save(fileName.getAbsolutePath());
        System.out.println(fileName.getAbsolutePath());
        return fileName.getAbsolutePath();
    }

    public TextFragment generateTitle(String text){
        TextFragment header = new TextFragment(text.toUpperCase());
        header.getTextState().setFont(FontRepository.findFont("Arial-Bold"));
        header.getTextState().setFontSize(20);
        header.setHorizontalAlignment (HorizontalAlignment.Center);
        header.setVerticalAlignment(VerticalAlignment.Center);
        //header.setPosition(new Position(120, 760));
        header.setMargin(new MarginInfo(0, 10, 0 ,0));
        return header;
    }

    public TextFragment generateTopic(String text){
        TextFragment topic = new TextFragment(text);
        topic.getTextState().setFont(FontRepository.findFont("Arial-Bold"));
        topic.getTextState().setFontSize(18);
        topic.setHorizontalAlignment (HorizontalAlignment.Left);
        topic.setMargin(new MarginInfo(0, 12, 0, 20));

        //patientName.setPosition(new Position(130, 820));
       return topic;
    }

    public TextFragment generateSmallText(String text, aligment aligment){
        TextFragment smallText = new TextFragment(text);
        smallText.getTextState().setFont(FontRepository.findFont("Arial"));
        smallText.getTextState().setFontSize(14);
        smallText.setHorizontalAlignment (getAlignment(aligment));
        smallText.setMargin(new MarginInfo(0, 2, 0, 0));
        return smallText;
    }
    public TextFragment generateSmallText(String text){
        TextFragment smallText = new TextFragment(text);
        smallText.getTextState().setFont(FontRepository.findFont("Arial"));
        smallText.getTextState().setFontSize(14);
        smallText.setHorizontalAlignment (HorizontalAlignment.Left);
        smallText.setMargin(new MarginInfo(0, 2, 0, 0));
        return smallText;
    }
    public TextFragment generateMediumText(String text, aligment aligment){
        TextFragment smallText = new TextFragment(text);
        smallText.getTextState().setFont(FontRepository.findFont("Arial-Bold"));
        smallText.getTextState().setFontSize(16);
        smallText.setHorizontalAlignment (getAlignment(aligment));
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

    public enum aligment{
        Right,
        Left,
        Center
    }

    public int getAlignment(aligment aligment){
        switch (aligment){
            case Right:
                return 3;
            case Left:
                return 1;
            case Center:
                return 2;
            default:
                return 1;

        }
    }


}
