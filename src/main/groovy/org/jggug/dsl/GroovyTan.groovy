package org.jggug.dsl

class GroovyTan {
    def directions = []
    def move(Direction direction) {
        directions << direction
    }
}