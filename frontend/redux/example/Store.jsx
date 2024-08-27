import { configureStore } from '@reduxjs/toolkit';
import { persistStore, persistReducer, FLUSH,REHYDRATE,PAUSE,PERSIST,PURGE,REGISTER} from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // 기본 로컬 스토리지
import { combineReducers } from 'redux';
import ExampleReducer from "./ExampleReducer.js"; // combineReducers 임포트

// 리듀서 결합
const rootReducer = combineReducers({
    example: ExampleReducer,
});

// persistConfig 설정
const persistConfig = {
    key: 'root', // 스토어의 키
    storage, // 상태를 저장할 스토리지
};

// 리듀서를 persistReducer로 감싸기
const persistedReducer = persistReducer(persistConfig, rootReducer);

// 스토어 생성
export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: {
                ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
            },
        }),
});

// persistor 생성
export const persistor = persistStore(store);
