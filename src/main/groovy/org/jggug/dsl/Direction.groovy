package org.jggug.dsl

enum Direction {

    left(-100, 0),
    right(100, 0),
    up(0, -100),
    down(0, 100);

    private double x, y;

    private Direction(double x, double y) {
        this.x = x
        this.y = y
    }
}