package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.domain.BrainstormMessage;
import com.example.demo.domain.BrainstormMessageRepository;

@Service
@Transactional
public class BrainstormMessageService {

    private final BrainstormMessageRepository repository;

    public BrainstormMessageService(BrainstormMessageRepository repository) {
        this.repository = repository;
    }

    // 메시지 저장 (전체 JSON 저장)
    public BrainstormMessage save(BrainstormMessage message) {
        return repository.save(message);
    }
    
    public BrainstormMessage createMessage(BrainstormMessage request) {
        return repository.save(request);
    }

    // SpaceId 기반 메시지 조회
    public List<BrainstormMessage> findBySpaceId(Long spaceId) {
        return repository.findBySpaceId(spaceId);
    }

    // 전체 업데이트 (추천수, 위치, 메시지, 색상 등)
    public BrainstormMessage update(Long id, BrainstormMessage req) {
        BrainstormMessage msg = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        msg.setMessage(req.getMessage());
        msg.setCategory(req.getCategory());
        msg.setColor(req.getColor());
        msg.setVotes(req.getVotes());
        msg.setPosX(req.getPosX());
        msg.setPosY(req.getPosY());

        return repository.save(msg);
    }

    // 삭제
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
