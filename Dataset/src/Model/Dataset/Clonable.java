/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dataset;

import Model.Datapoint.Datapoint_Edge;

/**
 *
 * @author kenna
 */
interface Clonable {
    
    public Dataset getClone(Datapoint_Edge type);
}
