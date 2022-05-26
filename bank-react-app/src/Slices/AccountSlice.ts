import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { IAccount } from "../Interfaces/IAccount";

interface AccountSliceState {
    loading: boolean,
    error: boolean,
    accounts?: IAccount[]
};

const initialAccountState: AccountSliceState = {
    loading: false,
    error: false
};

export const getAccounts = createAsyncThunk(
    "accounts/get",
    async (username: string, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/accounts/${username}`);
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
);

export const AccountSlice = createSlice({
    name: 'accounts',
    initialState: initialAccountState,
    reducers: {
        clearAccounts: (state) => {
            state.accounts = undefined
        }
    },
    extraReducers: (builder) => {
        builder.addCase(getAccounts.pending, (state, action) => {
            state.loading = true;
        });

        builder.addCase(getAccounts.fulfilled, (state, action) => {
            state.accounts = action.payload;
            state.loading = false;
            state.error = false;
        });

        builder.addCase(getAccounts.rejected, (state, action) => {
            state.error = true;
            state.loading = false;
        })
    }
})

export const {clearAccounts} = AccountSlice.actions;

export default AccountSlice.reducer;