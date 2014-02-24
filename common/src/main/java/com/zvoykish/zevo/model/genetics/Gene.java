package com.zvoykish.zevo.model.genetics;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 16:13:46
 */
public interface Gene extends Serializable {
    String toString();

    Component getAsComponent();
}
