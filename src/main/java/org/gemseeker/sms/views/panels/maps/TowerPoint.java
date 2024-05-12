package org.gemseeker.sms.views.panels.maps;

import org.gemseeker.sms.data.Tower;

public class TowerPoint {

    private Tower childTower;
    private Tower parentTower;

    public TowerPoint(Tower child, Tower parent) {
        childTower = child;
        parentTower = parent;
    }

    public Tower getChildTower() {
        return childTower;
    }

    public void setChildTower(Tower childTower) {
        this.childTower = childTower;
    }

    public Tower getParentTower() {
        return parentTower;
    }

    public void setParentTower(Tower parentTower) {
        this.parentTower = parentTower;
    }
}
