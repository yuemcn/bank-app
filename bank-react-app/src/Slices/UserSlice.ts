import {createSlice, createAsyncThunk} from "@reduxjs/toolkit"
import axios from "axios"
import { Type } from "typescript"
import { IAccount } from "../Interfaces/IAccount"
import { IUser } from "../Interfaces/IUser"

// Figure out our default state for this slice

interface UserSliceState {
    loading: boolean, // whenever we are loading the user
    error: boolean, // user was or was not able to log in
    user?: IUser,
    accounts?: IAccount[]
}

const initialUserState: UserSliceState = {
    loading: false, // start out not loading anything
    error: false
}

type Login = {
    username: string,
    password: string
}

type UserInfo = {
    firstname: string,
    lastname: string,
    ssn: number,
    email: string,
    username: string,
    password: string,
    type: Type | any
}

export const loginUser = createAsyncThunk(
    'user/login',
    async (credentials:Login, thunkAPI) => {
        try {
            const res = await axios.post('http://localhost:8000/users/login', credentials);
            return {
                firstname: res.data.firstname,
                lastname: res.data.lastname,
                email: res.data.email,
                username: res.data.username,
                type: res.data.type,            
            }
        } catch (e) {
            return thunkAPI.rejectWithValue("something went wrong");
        }
    }
)

export const getUserDetails = createAsyncThunk(
    'users/get',
    async (username:string, thunkAPI) => {
        try {
            const res = await axios.get(`http://localhost:8000/users/${username}`);

            return {
                firstname: res.data.firstname,
                lastname: res.data.lastname,
                ssn: res.data.ssn,
                email: res.data.email,
                username: res.data.username,
                type: res.data.type,
            }
        } catch(error) {
            console.log(error);
        }
    }
)

export const logoutUser = createAsyncThunk(
    "user/logout",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get("http://localhost:8000/users/logout");
        } catch (e) {
            console.log(e);
        }
    }
)

export const registerUser = createAsyncThunk(
    "user/register",
    async (userInfo:UserInfo, thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.post("http://localhost:8000/users/register", userInfo);
        } catch (e) {
            console.log(e);
        }
    }
)

// create the slice

export const UserSlice = createSlice({
    name: "user", // name user because that is what we are storing inside the slice
    initialState: initialUserState,
    reducers: {
        toggleError: (state) => {
            state.error = !state.error;
        }
    },
    extraReducers: (builder) => {

        // loginUser

        builder.addCase(loginUser.pending, (state) => {
            state.loading = true;
        });
        builder.addCase(loginUser.fulfilled, (state, action) => {
            state.user = action.payload;
            state.error = false;
            state.loading = false;
        });
        builder.addCase(loginUser.rejected, (state) => {
            state.error = true;
            state.loading = false;
        });

        // logoutUser

        builder.addCase(logoutUser.fulfilled, (state) => {
            state.user = undefined;
            state.error = false;
            state.loading = false;
        })

        // register user

        builder.addCase(registerUser.fulfilled, (state, action) => {
            state.error = false;
            state.loading = false;
        })

    }
})

// If we had normal actions and reducers, we would export them like this
export const {toggleError} = UserSlice.actions;

export default UserSlice.reducer;