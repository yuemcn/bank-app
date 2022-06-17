import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { IAccount } from "../Interfaces/IAccount";
import { IUser } from "../Interfaces/IUser";

interface AccountSliceState {
    loading: boolean,
    error: boolean,
    userAccounts?: IAccount[],
    allAccounts?: IAccount[]
};

const initialAccountState: AccountSliceState = {
    loading: false,
    error: false
};

export const getAccounts = createAsyncThunk(
    "accounts/get",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/accounts/`);
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
);

export const createAccount = createAsyncThunk(
    "accounts/create",
    async (user: IUser, thunkAPI) => {
        try {
            const res = await axios.post("http://localhost:8000/accounts");
            let account: IAccount = {
                accountNumber: res.data.accountNumber,
                user: user,
                balance: res.data.balance,
                status: res.data.status
            }
        } catch (e) {
            console.log(e);
        }
    }
)

export const AccountSlice = createSlice({
    name: 'accounts',
    initialState: initialAccountState,
    reducers: {
        clearAccounts: (state) => {
            state.userAccounts = undefined;
            state.allAccounts = undefined;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(getAccounts.pending, (state) => {
            state.loading = true;
        });

        builder.addCase(getAccounts.fulfilled, (state, action) => {
            state.userAccounts = action.payload;
            state.loading = false;
            state.error = false;
        });

        builder.addCase(getAccounts.rejected, (state) => {
            state.error = true;
            state.loading = false;
        })
    }
})

export const {clearAccounts} = AccountSlice.actions;

export default AccountSlice.reducer;