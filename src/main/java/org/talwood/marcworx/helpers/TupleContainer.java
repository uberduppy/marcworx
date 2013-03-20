/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.talwood.marcworx.helpers;

import java.io.Serializable;

public class TupleContainer<K extends Comparable<K>, T extends Comparable<T>> implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private K leftSide;
    private T rightSide;
    
    public TupleContainer(K leftSide, T rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Left: ").append(leftSide.toString()).append(" Right:").append(rightSide.toString());
        return sb.toString();
        
    }
    
    @Override
    public int hashCode() {
        return leftSide.hashCode() + (37 * rightSide.hashCode());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TupleContainer<K, T> other = (TupleContainer<K, T>) obj;
        if (this.leftSide != other.leftSide && (this.leftSide == null || !this.leftSide.equals(other.leftSide))) {
            return false;
        }
        if (this.rightSide != other.rightSide && (this.rightSide == null || !this.rightSide.equals(other.rightSide))) {
            return false;
        }
        return true;
    }
    

    public K getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(K leftSide) {
        this.leftSide = leftSide;
    }

    public T getRightSide() {
        return rightSide;
    }

    public void setRightSide(T rightSide) {
        this.rightSide = rightSide;
    }


    @Override
    public int compareTo(Object obj) {
        int result = 0;
        if(obj instanceof TupleContainer) {
            TupleContainer<K, T> rhs = (TupleContainer<K, T>) obj;
            // Only compare if class types are the same
            if(rhs.getLeftSide().getClass().equals(leftSide.getClass()) && rhs.getRightSide().getClass().equals(rightSide.getClass())) {
                if(rhs.getLeftSide() == null) {
                    if(leftSide != null) {
                        result = -1;
                    }
                } else {
                    if(leftSide == null) {
                        result = 1;
                    } else {
                        result = leftSide.compareTo(rhs.getLeftSide());
                    }
                }
                if(result == 0) {
                    if(rhs.getRightSide() == null) {
                        if(rightSide != null) {
                            result = -1;
                        }
                    } else {
                        if(rightSide == null) {
                            result = 1;
                        } else {
                            result = rightSide.compareTo(rhs.getRightSide());
                        }
                    }
                }
            } else {
                throw new ClassCastException("Can only compare two TupleContainers with same sub types");
            }
            
        } else {
            throw new ClassCastException("Can only compare two TupleContainers");
        }
        
        return result;
    }
}