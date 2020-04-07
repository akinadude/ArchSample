package com.ooma.archsample.domain.usecase

abstract class BaseUseCase <out Type, in Params> {

    //todo Successors could build Observable|Completable use cases also.
    // Need to create Observable|CompletableUseCase in this case.
}