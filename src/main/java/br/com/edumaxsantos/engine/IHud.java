package br.com.edumaxsantos.engine;

public interface IHud {

    GameItem[] getGameItems();

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem: gameItems) {
            gameItem.getMesh().cleanup();
        }
    }
}
