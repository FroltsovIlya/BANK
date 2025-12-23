package com.example.bank.web;

import com.example.bank.card.Card;
import com.example.bank.service.CardService;
import com.example.bank.user.User;
import com.example.bank.web.dto.CardActivationRequestDto;
import com.example.bank.web.dto.TransferRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 👤 получить карты пользователя
    @GetMapping
    public List<Card> getUserCards(
            @RequestParam String username
    ) {
        return cardService.getUserCards(
                new User(username, "", false)
        );
    }

    // 💸 перевод денег
    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest request) {
        cardService.transfer(
                request.getFromCard(),
                request.getToCard(),
                request.getAmount()
        );
    }

    // 🆕 запрос на выпуск карты
    @PostMapping("/activate")
    public void requestActivation(
            @RequestParam String username,
            @RequestBody CardActivationRequestDto request
    ) {
        cardService.requestCardActivation(
                request.getCardNumber(),
                new User(username, "", false)
        );
    }

    // 👮 админ: подтвердить выпуск карты
    @PostMapping("/activate/approve")
    public void approveActivation(
            @RequestParam String adminName,
            @RequestBody CardActivationRequestDto request
    ) {
        cardService.approveActivation(
                request.getCardNumber(),
                new User(adminName, "", true)
        );
    }
}
