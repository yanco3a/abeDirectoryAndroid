package com.directory.abe.FeedbackFeature.UseCases;

import com.directory.abe.FeedbackFeature.Services.APIFeedbackService;

public class FeedbackUseCase implements IFeedbackUseCase {
    private static final String TAG = FeedbackUseCase.class.getSimpleName();
    private APIFeedbackService apiFeedbackService = new APIFeedbackService(this);
    private IFeedbackPresenter iFeedbackPresenter;

    public FeedbackUseCase(IFeedbackPresenter iFeedbackPresenter) {
        this.iFeedbackPresenter = iFeedbackPresenter;
    }

    public void attemptFeedback(String q1, String q2, String q3, String q4, String q4a, String q5, String q6, String q7, String q8, String q9, String email) {
        if (this.apiFeedbackService != null) {
            this.apiFeedbackService.attemptFeedbackResult(q1, q2, q3, q4, q4a, q5, q6, q7, q8, q9, email);
        }
    }

    public void onFeedbackFailure(String s) {
        if (this.iFeedbackPresenter != null) {
            this.iFeedbackPresenter.onFeedbackFailure("abe isn't feeling so good right now");
        }
    }

    public void onFeedbackSuccess(String booleanStr) {
        if (this.iFeedbackPresenter == null) {
            return;
        }
        if (booleanStr == null) {
            this.iFeedbackPresenter.onFeedbackFailure("abe isn't feeling so good right now");
        } else if (booleanStr.equals("true") || booleanStr.equals("True")) {
            this.iFeedbackPresenter.onFeedbackSuccess("Thank you for your feedback");
        } else {
            this.iFeedbackPresenter.onFeedbackFailure("abe isn't feeling so good right now");
        }
    }
}
