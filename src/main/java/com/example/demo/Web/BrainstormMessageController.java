package com.example.demo.Web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.domain.BrainstormMessage;
import com.example.demo.service.BrainstormMessageService;

@RestController
@RequestMapping("/api/brainstorm/messages")
public class BrainstormMessageController {

    private final BrainstormMessageService service;

    public BrainstormMessageController(BrainstormMessageService service) {
        this.service = service;
    }

    // CREATE 메시지 POST
    @PostMapping
    public ResponseEntity<BrainstormMessage> createMessage(@RequestBody BrainstormMessage request) {
        BrainstormMessage saved = service.createMessage(request);
        return ResponseEntity.ok(saved);
    }

    // 특정 공간의 메시지 전체 조회
    @GetMapping("/{spaceId}")
    public ResponseEntity<List<BrainstormMessage>> getMessages(@PathVariable Long spaceId) {
        return ResponseEntity.ok(service.findBySpaceId(spaceId));
    }

    // UPDATE (위치, 추천 등)
    @PutMapping("/{id}")
    public ResponseEntity<BrainstormMessage> updateMessage(@PathVariable Long id, @RequestBody BrainstormMessage req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
