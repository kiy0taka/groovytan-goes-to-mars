package org.jggug.dsl

import static groovyx.javafx.GroovyFX.start
import org.codehaus.groovy.control.CompilerConfiguration
import java.util.concurrent.FutureTask;
import javafx.application.Platform;

def imageURL = getClass().getResource('/images/groovytan.png').toExternalForm()

start {
    popup = popup(autoHide: true) {
        borderPane {
            center { script = textArea() }
            bottom {
                hbox {
                    button 'Run', onAction: {
                        def groovyTan = new GroovyTan()
                        def binding = new Binding(groovyTan:groovyTan, *:Direction.values().collectEntries { [(it.name()):it]} )
                        def config = new CompilerConfiguration()
                        def shell = new GroovyShell(GroovyTanView.classLoader, binding, config)
                        shell.evaluate(script.text)
                        popup.hide()
                        sequentialTransition {
                            groovyTan.directions.each { direction ->
                                translateTransition(1.s, byX:direction.x, byY:direction.y, node:img)
                            }
                        }.play()
                    }
                    button 'Close', onAction: { popup.hide() }
                }
            }
        }
    }
    stage(title:'Groovyたん', visible:true) {
        scene(width:1024, height:768, fill:BLACK) {
            arc(centerX:1024, centerY:0, radiusX:250, radiusY:250, startAngle:180, length:90, fill:linearGradient(stops:[RED,YELLOW]), type:'round')
            img = imageView(x:0, y:553, fitWidth:0, fitHeight:0) {
                image(imageURL, width:110, height:215)
            }
            def move = { sec, x, y, evt = null ->
                translateTransition(sec.s, byX:x, byY:y, node:img).play()
            }
            hbox {
                button 'Left', onAction: move.curry(1, -100, 0)
                button 'Right', onAction: move.curry(1, 100, 0)
                button 'Up', onAction: move.curry(1, 0, -100)
                button 'Down', onAction: move.curry(1, 0, 100)
                button 'Reset', onAction: { translateTransition(0.5.s, toX:0-img.x, toY:img.y-553, node:img).play() }
                button 'DSL', onAction: {
                    popup.show(primaryStage)
                }
            }
        }
    }
}

