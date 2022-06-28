import { createAsyncThunk, createSlice, Update } from "@reduxjs/toolkit";
import axios from "axios";
import { IAccount } from "../Interfaces/IAccount";
import { IUser } from "../Interfaces/IUser";
import { Status } from "../Interfaces/Status";

interface AccountSliceState {
    loading: boolean,
    error: boolean,
    currentAccount?: IAccount,
    userAccounts?: IAccount[],
    allAccounts?: IAccount[],
    active?: IAccount[],
    inactive?: IAccount[],
    deactivated?: IAccount[]
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
            axios.defaults.withCredentials = true;
            const res = await axios.post("http://localhost:8000/accounts");
            let account: IAccount = {
                accountNumber: res.data.accountNumber,
                user: user,
                balance: res.data.balance,
                status: res.data.status
            }
            return account; // recently added this line
        } catch (e) {
            console.log(e);
        }
    }
)

export const getAllAccounts = createAsyncThunk(
    "accounts/all-accounts",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get("http://localhost:8000/accounts/all-accounts");
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

export const getAllActive = createAsyncThunk(
    "accounts/active",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post("http://localhost:8000/accounts/active");
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

export const getAllInactive = createAsyncThunk(
    "accounts/inactive",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get("http://localhost:8000/accounts/inactive");
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

export const getAllDeactivated = createAsyncThunk(
    "accounts/deactivated",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get("http://localhost:8000/accounts/deactivated");
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

type UpdateInfo = {
    accountNumber: number,
    status: string
}

export const updateAccountStatus = createAsyncThunk(
    "accounts/update-status",
    async (info: UpdateInfo, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            console.log(info);
            const res = await axios.put("http://localhost:8000/accounts/", info);
            console.log(res);
        } catch (e) {
            console.log(e);
        }
    }
)

// reducers

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
        });

        // create account

        builder.addCase(createAccount.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.userAccounts?.push(action.payload!);
            state.allAccounts?.push(action.payload!);
        });

        // get all accounts

        builder.addCase(getAllAccounts.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.allAccounts = action.payload;
        });

        // get all active

        builder.addCase(getAllActive.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.active = action.payload;
        });

        // get all inactive

        builder.addCase(getAllInactive.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.inactive = action.payload;
        });

        // get all deactivated

        builder.addCase(getAllDeactivated.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.deactivated = action.payload;
        });

        // update account status

        builder.addCase(updateAccountStatus.fulfilled, (state) => {
            state.error = false;
            state.loading = false;
        })
    }
})

export const {clearAccounts} = AccountSlice.actions;

export default AccountSlice.reducer;