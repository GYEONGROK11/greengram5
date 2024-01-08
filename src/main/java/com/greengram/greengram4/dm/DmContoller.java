package com.greengram.greengram4.dm;

import com.greengram.greengram4.common.ResVo;
import com.greengram.greengram4.dm.model.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dm")
@Tag(name = "디엠 API",description = "디엠 관련 처리")
public class DmContoller {
    private final DmService service;

    @PostMapping("/msg")
    public ResVo postDmMsg(@RequestBody DmMsgInsDto dto){
        return service.postDmMsg(dto);
    }

    @PostMapping
    public DmSelVo postDm(@RequestBody DmInsDto dto){
        return service.postDm(dto);
    }

    @GetMapping
    public List<DmSelVo> getDmAll(DmSelDto dto){
        return service.getDmAll(dto);
    }

    @GetMapping("/msg")
    public List<DmMsgSelVo> getMsAll(DmMsgSelDto dto){
        log.info("dto : {}",dto);
        return service.getMsAll(dto);
    }

    @DeleteMapping("/msg")
    public ResVo delDmMsg(DmMsgDelDto dto){

        return service.delDmMsg(dto);
    }
}
