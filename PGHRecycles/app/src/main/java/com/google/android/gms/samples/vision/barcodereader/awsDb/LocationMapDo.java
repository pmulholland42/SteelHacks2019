package com.google.android.gms.samples.vision.barcodereader.awsDb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "nosqlrec-mobilehub-42069-locations")
public class LocationMapDo {

    private String _municipality;
    private Integer _typeId;

    public void set_typeId(Integer _typeId) {
        this._typeId = _typeId;
    }

    @DynamoDBHashKey(attributeName = "typeId")
    @DynamoDBAttribute(attributeName = "typeId")
    public Integer get_typeId() {
        return _typeId;
    }

    @DynamoDBHashKey(attributeName = "municipality")
    @DynamoDBAttribute(attributeName = "municipality")
    public String get_municipality() {
        return _municipality;
    }

    public void set_municipality(String _municipality) {
        this._municipality = _municipality;
    }
}
