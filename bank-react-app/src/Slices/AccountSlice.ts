import { createAsyncThunk, createSlice, Update } from "@reduxjs/toolkit";
import axios from "axios";
import { IAccount } from "../Interfaces/IAccount";
import { IUser } from "../Interfaces/IUser";

interface AccountSliceState {
    loading: boolean,
    error: boolean,
    current?: IAccount,
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

// ----------------------------------------------------------------------------

// get accounts

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

// create account

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

// get all accounts

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

// get all active accounts

export const getAllActive = createAsyncThunk(
    "accounts/active",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get("http://localhost:8000/accounts/active");
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

// get all inactive accounts

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

// get all deactivated accounts

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

// update account status

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

// get account details

export const getAccountDetails = createAsyncThunk(
    "accounts/account-details",
    async (accountNumber: number, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get(`http://localhost:8000/accounts/details/${accountNumber}`);
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
);

// reducers -------------------------------------------------------------------

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

        // get account details

        builder.addCase(getAccountDetails.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
            state.current = action.payload;
        });
    }
})

export const {clearAccounts} = AccountSlice.actions;

export default AccountSlice.reducer;