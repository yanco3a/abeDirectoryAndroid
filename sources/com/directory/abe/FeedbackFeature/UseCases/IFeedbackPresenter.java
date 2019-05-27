package com.directory.abe.FeedbackFeature.UseCases;

public interface IFeedbackPresenter {
    void onFeedbackFailure(String str);

    void onFeedbackSuccess(String str);

    void sendLongMsgToUser(String str);
}
