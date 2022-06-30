import { configureStore } from "@reduxjs/toolkit";

import userReducer from "./Slices/UserSlice";
import accountReducer from "./Slices/AccountSlice";
import transactionReducer from "./Slices/TransactionSlice";

export const store = configureStore({
    reducer: {
        user: userReducer,
        accounts: accountReducer,
        transactions: transactionReducer
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;