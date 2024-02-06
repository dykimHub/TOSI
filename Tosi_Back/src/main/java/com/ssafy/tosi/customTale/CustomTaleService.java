package com.ssafy.tosi.customTale;

import com.ssafy.tosi.tale.TaleDto;
import com.ssafy.tosi.taleDetail.Page;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomTaleService {

    private final CustomTaleRepository customTaleRepository;

    @Autowired
    public CustomTaleService(CustomTaleRepository customTaleRepository) {
        this.customTaleRepository = customTaleRepository;
    }


    //customTaleId에 해당하는 CustomTale 엔터티 (커스텀 동화 상세조회)
    public Optional<CustomTale> getCustomTale(Integer customTaleId) {
        return customTaleRepository.findById(customTaleId);
    }

    //userId에 해당하는 CustomTale 엔터티를 조회 (나의 책장 - 내가 만든 동화)
    public List<CustomTale> getCustomTalesByUserId(Integer userId) {
        // Implement your custom query method in the repository if needed
        return customTaleRepository.findByUserId(userId);
    }

    //isPublic이 true인 CustomTale 엔터티를 조회 (친구들이 만든 동화보기 목록)
    public List<CustomTale> getCustomTales() {
        return customTaleRepository.findByIsPublic(true);
    }

    //CustomTale 엔터티를 저장 (내가 만드는 동화 저장)
    public CustomTale postCustomTale(CustomTale customTale) {
        return customTaleRepository.save(customTale);
    }

    //customTaleId에 해당하는 CustomTale 엔터티의 isPublic 값 변경 (나의 책장 - 내가 만든 동화)
    public CustomTale putCustomTale(Integer customTaleId, boolean isPublic) {
        Optional<CustomTale> optionalCustomTale = customTaleRepository.findById(customTaleId);

        if (optionalCustomTale.isPresent()) {
            CustomTale customTale = optionalCustomTale.get();
            customTale.setPublic(isPublic); //JavaBeans 규악에 따라 setIsPublic가 아니라 setPublic라는 세터 메소드를 생성합니다.
            return customTaleRepository.save(customTale);
        } else {
            return null;
        }
    }

    //customTaleId에 해당하는 CustomTale 엔터티를 삭제
    public boolean deleteCustomTale(Integer customTaleId) {
        customTaleRepository.deleteById(customTaleId);
        return true;
    }

    public String split_sentences(String string) throws IOException {
        String[] contents = new String[]{string};
        String[] splitted_contents = new String[contents.length];
        File[] files = new File[contents.length];

        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null) continue;

            files[i] = File.createTempFile("content" + i, ".txt");
            System.out.print(files[i]);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(files[i]))) {
                writer.write(contents[i]);
            }

            ProcessBuilder builder = new ProcessBuilder("node", "src/main/java/com/ssafy/tosi/taleDetail/morpheme/process.js", files[i].getAbsolutePath());
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line + "\n");
            }

            splitted_contents[i] = response.toString();

        }
        System.out.print(splitted_contents[0]);
        return splitted_contents[0];

    }

    public List<Page> paging(String[] splitted_contents, TaleDto taleDto) {
        int p = 1;
        List<Page> pages = new ArrayList<>();

        for (int i = 0; i < splitted_contents.length; i++) {
            if (splitted_contents[i] == null)
                continue;

            String[] sentences = splitted_contents[i].split("\n");

            for (int j = 0; j < sentences.length - 1; j += 2) {
                Page page = Page.builder()
                        .leftNo(p)
                        .left(taleDto.getImages()[i])
                        .rightNo(p + 1)
                        .right(sentences[j] + "\n" + sentences[j + 1])
                        .build();

                pages.add(page);
                p += 2;
            }


        }

        return pages;
    }
}
