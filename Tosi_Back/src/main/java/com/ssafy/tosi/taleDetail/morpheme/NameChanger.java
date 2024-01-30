package com.ssafy.tosi.taleDetail.morpheme;

import com.ssafy.tosi.taleDetail.morpheme.kiwi.KiwiTag;
import kr.pe.bab2min.Kiwi;

import java.util.*;

public class NameChanger {
    private static final String modelPath = "src/main/java/com/ssafy/tosi/taleDetail/morpheme/kiwi/ModelGenerator";
    private static final Josa josa = new Josa();
    private static Kiwi kiwi = null;
    private static KiwiTag kiwiTag;
    private static StringBuilder changingContent;


    Kiwi getKiwi() throws Exception {
        if (kiwi == null) {
            kiwi = Kiwi.init(modelPath);
        }
        return kiwi;
    }

    public String changeName(String content, Map<String, String> nameMap) throws Exception {
        // nameMap = new HashMap<>();
        // nameMap.put("토끼","다윤");
        // nameMap.put("자라","지민");

        // cnames에 이름이 바뀔 등장인물 저장
        List<String> cnames = new ArrayList<>();
        cnames.addAll(nameMap.keySet());

        //등장인물마다 숫자를 지정해서 0,1,2,… 으로 바꿈
        for (int i = 0; i < cnames.size(); i++) {
            content = content.replace(cnames.get(i), String.valueOf(i));
        }

        changingContent = new StringBuilder();
        String[] sentences = content.split("\n");

        for (String sent : sentences) {
            String sentCopy = sent;
            Kiwi.Token[] tokens = getKiwi().tokenize(sent, Kiwi.Match.allWithNormalizing); // 형태소 분석
            System.out.println(Arrays.toString(tokens));

            for (int i = tokens.length - 2; i >= 0; i--) {
                String word = "";

                // 형태소가 숫자이면
                try {
                    Integer.parseInt(tokens[i].form);
                } catch (NumberFormatException e) {
                    continue;
                }

                // word에 아이 이름 넘겨주고 조사 분석
                word = nameMap.get(cnames.get(Integer.parseInt(tokens[i].form)));
                String myjosa = appendJosa(tokens[i + 1], word);

                int start, end;

                if (myjosa != null) {
                    start = tokens[i + 1].position;
                    end = start + tokens[i + 1].length;
                    sentCopy = sentCopy.substring(0, start) + myjosa + sentCopy.substring(end);
                } else if (KiwiTag.toString(tokens[i + 1].tag).equals("VCP")) { // 긍정지시사 '이'
                    start = tokens[i + 1].position;
                    end = start + tokens[i + 2].length;

                    if (tokens[i + 2].form.equals("아") || tokens[i + 2].form.equals("야")) {
                        myjosa = josa.aYa(word);
                    } else if (KiwiTag.toString(tokens[i + 2].tag).equals("ETM")) {
                        myjosa = josa.eNoE(word) + tokens[i + 2].form;
                    } else {
                        myjosa = josa.eNoE(word);
                    }
                    sentCopy = sentCopy.substring(0, start) + myjosa + sentCopy.substring(end);


                } else continue;


            }
            changingContent.append(sentCopy).append("\n");

        }

        String changedContent = changingContent.toString();

        // 숫자를 아이 이름으로 변환
        for (int i = 0; i < cnames.size(); i++) {
            changedContent = changedContent.replace(String.valueOf(i), nameMap.get(cnames.get(i)));
        }

        return changedContent;
    }

    public String appendJosa(Kiwi.Token nextToken, String word) {
        String form = nextToken.form;
        String nextTag = KiwiTag.toString(nextToken.tag);

        switch (nextTag) {
            case "JKS": // 주격 조사
            case "ETM": // 관형형 전성 어미
            case "MM":
                return form.equals("이") || form.equals("가") ? josa.iGa(word) : josa.eunNeun(word);
            case "JX": // 보조사
                return form.equals("아") || form.equals("야") ? josa.aYa(word) : josa.eunNeun(word);
            case "JKO": // 목적격 조사
                return josa.eulReul(word);
            case "JKC": // 보격 조사
                return josa.iGa(word);
            case "JKB": // 부사격 조사
                return form.equals("로") || form.equals("으로") ? josa.euroRo(word) : josa.eNoE(word) + form;
            case "JKV": // 호격 조사
                return josa.aYa(word);
            case "JC": // 접속 조사
                return josa.gwaWa(word);
            case "XSN": // 명사 파생 접미사
            case "JKG": // 관형격 조사
                if (form.equals("님")) {
                    return form;
                }
                return josa.eNoE(word) + form;
            case "NNG":
                if (form.equals("와") || form.equals("과")) {
                    return josa.gwaWa(word);
                } else if (form.equals("은") || form.equals("는")) {
                    return josa.eunNeun(word);
                } else if (form.equals("을") || form.equals("를")) {
                    return josa.eulReul(word);
                }
            default:
                return null;

        }
    }

}

