import { configureStore } from "@reduxjs/toolkit";

import userReducer from "./Slices/UserSlice";
import accountReducer from "./Slices/AccountSlice";
import customersReducer from "./Slices/CustomersSlice";

export const store = configureStore({
    reducer: {
        user: userReducer,
        accounts: accountReducer,
        customers: customersReducer
    }
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;