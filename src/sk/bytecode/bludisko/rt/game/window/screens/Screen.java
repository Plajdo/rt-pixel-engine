package sk.bytecode.bludisko.rt.game.window.screens;

import sk.bytecode.bludisko.rt.game.window.Window;
import sk.bytecode.bludisko.rt.game.input.InputManager;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.ref.WeakReference;

public abstract class Screen {

    protected WeakReference<Window> window;

    // MARK: - Game loop

    public void tick(float dt) {
        getInputManager().tick(dt);
    }

    public abstract void draw(Graphics graphics);

    // MARK: - Public

    public abstract InputManager getInputManager();

    public void screenWillAppear(Window window) {
        this.window = new WeakReference<>(window);
    }

    public void screenDidAppear() {}

    public void screenDidChangeBounds(Rectangle bounds) {
        this.getInputManager().updateWindowDimensions(bounds);
    }

}