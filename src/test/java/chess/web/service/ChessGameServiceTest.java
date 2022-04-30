package chess.web.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.game.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.piece.StartedPawn;
import chess.domain.piece.position.Position;
import chess.domain.piece.property.Color;
import chess.web.dao.ChessBoardDao;
import chess.web.dao.PlayerDao;
import chess.web.dto.MoveDto;
import chess.web.dto.PlayResultDto;
import chess.web.service.fakedao.FakeChessBoardDao;
import chess.web.service.fakedao.FakePlayerDao;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChessGameServiceTest {

    ChessBoardDao chessBoardDao = new FakeChessBoardDao();
    PlayerDao playerDao = new FakePlayerDao();
    ChessGameService chessGameService = new ChessGameService(chessBoardDao, playerDao);

    @Test
    void start() {
        ChessGame chessGame = chessGameService.start();

        assertThat(chessGame.getBoard()).isNotNull();
    }

    @Test
    void play() {
        PlayResultDto playResultDto = chessGameService.play();

        Map<String, Piece> board = playResultDto.getBoard();
        String turn = playResultDto.getTurn();

        assertThat(board).isNotNull();
        assertThat(turn).isNotNull();
    }

    @Test
    void move() {
        chessBoardDao.save(Position.of("a2"), new StartedPawn(Color.WHITE));
        PlayResultDto playResultDto = chessGameService.move(new MoveDto("a2", "a4"));

        Map<String, Piece> board = playResultDto.getBoard();
        boolean isFinished = playResultDto.getIsFinished();

        assertThat(isFinished).isNotNull();
        assertThat(board).isNotNull();
    }
}
