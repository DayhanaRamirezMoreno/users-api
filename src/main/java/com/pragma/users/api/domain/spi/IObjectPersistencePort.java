package com.pragma.users.api.domain.spi;

import com.pragma.users.api.domain.model.ObjectModel;
import java.util.List;

public interface IObjectPersistencePort {
    ObjectModel saveObject(ObjectModel objectModel);

    List<ObjectModel> getAllObjects();
}