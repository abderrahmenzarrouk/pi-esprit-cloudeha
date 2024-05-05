package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.ResponsePost;

import com.example.pi_projet.service.IResponsePostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("ResponsePost")

public class ResponsePostController {
    IResponsePostService responsePostService;

    @PostMapping("/addResponsePost/{responsePost}/{idPost}/{idUser}")
    public ResponsePost addResp (@PathVariable String responsePost,@PathVariable Long idPost,@PathVariable Long idUser ){
        return  responsePostService.addResponsePost(responsePost,idPost,idUser);
    }


    @GetMapping("/listResponsePost/{idPost}")
    public List<ResponsePost> retrieveRes ( @PathVariable Long idPost){
        return responsePostService.retrieveAllResponsePerPost(idPost);
    }
}
