package com.directory.abe.FeedbackFeature.UseCases;

public interface IFeedbackUseCase {
    void onFeedbackFailure(String str);

    void onFeedbackSuccess(String str);
}
