package net.savagedev.lc.menu;

import net.savagedev.lc.menu.action.ClickAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuSlot {
    private final Set<ClickAction> actions = new HashSet<>();

    public void addAction(ClickAction action) {
        this.actions.add(action);
    }

    public Set<ClickAction> getActions() {
        return this.actions;
    }
}
