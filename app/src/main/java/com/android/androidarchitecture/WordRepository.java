package com.android.androidarchitecture;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void delete(Word word) {
        new deleteAsyncTask(mWordDao).execute(word);
    }

    public void deleteAllWords() {
        new deleteAllAsyncTask(mWordDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Word, Word, Word> {
        private WordDao mInsertAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mInsertAsyncTaskDao = dao;
        }

        @Override
        protected Word doInBackground(Word... words) {
            mInsertAsyncTaskDao.insert(words[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Word, Word, Word> {
        private WordDao mDeleteAsyncTaskDao;

        deleteAsyncTask(WordDao dao) {
            mDeleteAsyncTaskDao = dao;
        }

        @Override
        protected Word doInBackground(Word... words) {
            mDeleteAsyncTaskDao.delete(words[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Word, Word, Word> {
        private WordDao mDeleteAllAsyncTask;

        deleteAllAsyncTask(WordDao dao) {
            mDeleteAllAsyncTask = dao;
        }

        @Override
        protected Word doInBackground(Word... words) {
            mDeleteAllAsyncTask.deleteAll();
            return null;
        }
    }
}
