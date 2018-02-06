package com.thoughtworks.whatyourward.features.base;


/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that can be
 * accessed from the children classes by calling getView().
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    protected boolean isViewAttached() {
        return mvpView != null;
    }

    protected T getView() {
        return mvpView;
    }

    protected void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }


    private static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super(
                    "Please call Presenter.attachView(MvpView) before"
                            + " requesting data to the Presenter");
        }
    }

}
