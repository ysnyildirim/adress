package com.yil.adress.model;

import com.yil.adress.base.IEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "ExteriorDoorInteriorDoor")
public class ExteriorDoorInteriorDoor implements IEntity {
    @EmbeddedId
    private ExteriorDoorInteriorDoorPk id;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ExteriorDoorInteriorDoorPk implements Serializable {

        @ManyToOne
        @JoinColumn(name = "ExteriorDoorId",
                referencedColumnName = "Id",
                nullable = false,
                foreignKey = @ForeignKey(name = "FK_ExteriorDoorInteriorDoor_ExteriorDoorId_ExteriorDoor_Id"))
        private ExteriorDoor exteriorDoor;

        @ManyToOne
        @JoinColumn(name = "InteriorDoorId",
                referencedColumnName = "Id",
                nullable = false,
                foreignKey = @ForeignKey(name = "FK_ExteriorDoorInteriorDoor_InteriorDoorId_InteriorDoor_Id"))
        private InteriorDoor interiorDoor;

    }
}
