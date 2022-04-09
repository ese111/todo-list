package kr.codesquad.todolist.card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	public static final String ERROR_OF_CARD_ID_OR_USER_ID = "error of card id: %dor user id: %d";

	private final CardDao cardDao;

	@Transactional
	public CardDto.Redirection createCard(CardDto.WriteRequest request) {
		Card card = request.toEntity();
		Card cardInfo = cardDao.save(card);
		return new CardDto.Redirection(cardInfo.getTodoId(), cardInfo.getUserId());
	}

	public CardDto.WriteResponse readOf(Long id, Long userId) {
		String errorMessage = String.format(ERROR_OF_CARD_ID_OR_USER_ID, id, userId);
		Card cardInfo = cardDao.findByIdAndUserId(id, userId)
			.orElseThrow(() -> new IllegalArgumentException(errorMessage));
		return new CardDto.WriteResponse(cardInfo);
	}
}
