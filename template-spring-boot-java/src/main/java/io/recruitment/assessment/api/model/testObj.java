package io.recruitment.assessment.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")

public class testObj {

    @Id
    private int objID;
    private String objName;

    public testObj(){ }

    public testObj(int objID, String objName){
        this.objID = objID;
        this.objName = objName;
    }

    public testObj(testObj that){
        this.objID = that.objID;
        this.objName = that.objName;
    }

    public int getObjID(){
        return objID;
    }

    public String getObjName(){
        return objName;    
    }

    @Override
    public String toString() {
        return "testObj{" +
                "objID:\"" + objID + "\"," +
                "objName:\"" + objName + "\"}";
    }
    
}
