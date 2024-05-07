package com.example.pi_projet.controllers;

import com.example.pi_projet.entities.ResponsePost;

import com.example.pi_projet.service.IResponsePostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
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

    @PutMapping("/updateResponsePost")
    public ResponsePost update (@RequestBody ResponsePost responsePost){

        return  responsePostService.updateResponsePost(responsePost);
    }
    @GetMapping("/listResponsePost/{idPost}")
    public List<ResponsePost> retrieveRes ( @PathVariable Long idPost){
        return responsePostService.retrieveAllResponsePerPost(idPost);
    }

    @DeleteMapping("/deleteResponse/{idResponse}")
    public  void delete (@PathVariable Long idResponse){
        responsePostService.deleteResponsePost(idResponse);
    }
}
