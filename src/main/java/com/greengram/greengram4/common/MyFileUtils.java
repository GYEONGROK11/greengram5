package com.greengram.greengram4.common;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Component
@Getter
public class MyFileUtils {

    private final String uploadPrefixPath;
    //final은 객체 생성할때 초기화 해야됨

    public MyFileUtils(@Value("${file.dir}") String uploadPrefixPath) {
        this.uploadPrefixPath = uploadPrefixPath;
    }

    //폴더 만들기
    public String makeFolders(String path) {
        File folder = new File(uploadPrefixPath, path);
        folder.mkdirs(); //폴더를 만든다

        return folder.getAbsolutePath();
        //절대 주소 (풀네임)
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm() {
        String str = UUID.randomUUID().toString();
        log.info("123123:{}", str);
        return UUID.randomUUID().toString();
    }

    //확장자 얻어오기
    public String getExt(String fileNm) {

        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    //랜덤 파일명 만들기 with 확장자
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + getExt(originFileNm);
    }

    //랜덤 파일명 만들기 with 확장자 from MultipartFile
    public String getRandomFileNm(MultipartFile mf) {
        String fileNm = mf.getOriginalFilename();

        return getRandomFileNm(fileNm);
    }

    //메모리에 있는 내용을 파일로 옮기는 메소드
    public String transferTo(MultipartFile mf, String target) {
        String fileNm = getRandomFileNm(mf);
        String folderPath = makeFolders(target);
        File saveFile = new File(folderPath, fileNm); //파일 객체를 만들었다고
        saveFile.exists(); //파일이 무조건 존재하지는 않는다
        try {
            mf.transferTo(saveFile); //메모리에 있던 내용을 파일로 옮겨줌
            return fileNm; //랜덤 파일명을 리턴 : db에 저장을 해야되기 때문
            //return saveFile.getAbsolutePath(); (절대경로)이건 파일 주소 까지 db에 저장
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delFolderTrigger(String relativePath) {
        delFolder(uploadPrefixPath + relativePath);
    }

    public void delFolder(String folderPath) { //폴더와 그 아래에 폴더 및 파일 삭제
        File folder = new File(folderPath);
        if (folder.exists()) {
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    delFolder(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
            folder.delete();
        }
    }
}
