// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.lang;

import graphtea.platform.attribute.AtomAttribute;

import java.util.Vector;

/**
 * an eXtended data type that you can set it and also get it,
 * only if your value is in the predefined set.
 *
 * @see SetValidator
 * @see AtomAttribute
 *
 * @author Azin Azadi
 */
public class ArrayX<T> extends SetValidator<T> implements AtomAttribute<T> {
    public ArrayX(T initVal, T... x) {
        super.addValidValues(x);
        super.addValidValue(initVal);
        setValue(initVal);
    }

    private T value;

    public boolean setValue(T t) {
        if (isValid(t)) {
            value = t;
            return true;
        }
        return false;
    }

    public T getValue() {
        return value;
    }

    public String toString() {
        return super.toString() + " #$%# " + value.getClass().getName() + " #$%# " + value;
    }

}