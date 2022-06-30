import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axios from "axios"
import { ITransaction } from "../Interfaces/ITransaction"

interface TransactionSliceState {
    loading: boolean,
    error: boolean
    current?: ITransaction,
    all?: ITransaction[],
    accountTransactions?: ITransaction[]
}

const initialTransactionState: TransactionSliceState = {
    loading: false,
    error: false
}

// ----------------------------------------------------------------------------

// credit

type CreditInfo = {
    accountNumber: number,
    amount: number,
    description: string
}

export const credit = createAsyncThunk(
    "transactions/new/credit",
    async(info: CreditInfo, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post("http://localhost:8000/transactions/credit", info);
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

// reducers -------------------------------------------------------------------

export const TransactionSlice = createSlice({
    name: "transactions",
    initialState: initialTransactionState,
    reducers: {
        clearTransactions: (state) => {
            state.current = undefined;
            state.all = undefined;
            state.accountTransactions = undefined;
        }
    },
    extraReducers: (builder) => {

        builder.addCase(credit.fulfilled, (state, action) => {
            state.loading = false;
            state.error = false;
            state.accountTransactions?.push(action.payload);
            state.all?.push(action.payload);
        })
        
    }
});

export const {clearTransactions} = TransactionSlice.actions;
export default TransactionSlice.reducer;