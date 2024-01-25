package com.greengram.greengram4.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@ExtendWith(SpringExtension.class) //스프링컨테이너 테스트할때 필요 - 빈등록 된애를 객체화
@Import({MyFileUtils.class})
@TestPropertySource(properties = {
        "file.dir=D:/home/download",
})
public class MyFileUtilsTest {
    @Autowired //test 시는 생성자로 di가 안됨 그래서 오토와이드
    private MyFileUtils myFileUtils;

    @Test
    public void makeFolderTest(){
        String path = "/ggg12";
        File preFolder = new File(myFileUtils.getUploadPrefixPath(),path);
        //File은 컴퓨터에 있는 디렉토리 파일 컨트롤 가능
        assertEquals(false,preFolder.exists());

        String newPath = myFileUtils.makeFolders(path);
        File newFolder = new File(newPath);
        assertEquals(preFolder.getAbsolutePath(),newFolder.getAbsolutePath());
        assertEquals(true,newFolder.exists());

    }

    @Test
    public void getRandomFileNmTest(){
        String fileNm = myFileUtils.getRandomFileNm();
        System.out.println("fileNm : "+fileNm);
        assertNotNull(fileNm);
        assertNotEquals("",fileNm);
    }

    @Test
    public void getExt(){
        String fileNm = "abc.efg.eee.jpg";

        String ext = myFileUtils.getExt(fileNm);
        assertEquals(".jpg",ext);

        String fileNm2 = "jjj-asdfasdf.pnge";
        String ext2 = myFileUtils.getExt(fileNm2);
        assertEquals(".pnge",ext2);

    }

    @Test
    public void getRandomFileNmTest2(){
        String fileNm1 = "반갑당.아아.jpg";
        String rFileNm1 = myFileUtils.getRandomFileNm(fileNm1);
        System.out.println(rFileNm1);

        String fileNm2 = "반갑당.아아.jpeg";
        String rFileNm2 = myFileUtils.getRandomFileNm(fileNm2);
        System.out.println(rFileNm2);
    }

    @Test
    public void transferToTest() throws Exception {
        String fileNm = "신짱구.jpg";
        String filePath = "C:/Users/Administrator/Desktop/짱구 사진들/아기사진/해바라기반/" + fileNm;
        FileInputStream fis = new FileInputStream(filePath);
        MultipartFile mf = new MockMultipartFile("pic",fileNm,"jpg",fis);
        String saveFileNm = myFileUtils.transferTo(mf,"/feed/10");
        log.info("myFileUtils : {}",saveFileNm);
    }
}
