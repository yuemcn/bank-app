import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { Navbar } from "../../Components/Navbar/Navbar";
import { getUserDetails } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";

export const ProfilePage:React.FC = () => {

    const profile = useSelector((state:RootState) => state.user);

    const dispatch: AppDispatch = useDispatch();

    const { username } = useParams();

    useEffect(() => {
        console.log("Get the information about user: ", username);
        if (username && !profile.currentProfile) {
            dispatch(getUserDetails(username));
        }
        console.log("Current App State", profile);
    }, [profile]);

    return (
        <div>
            <Navbar />
            <br/>
            <h1>{profile.user?.firstname}'s Profile</h1>
            <br/>
            <h3>First Name: {profile.user?.firstname}</h3>
            <h3>Last Name: {profile.user?.lastname}</h3>
            <h3>Email: {profile.user?.email}</h3>
            <h3>Username: {profile.user?.username}</h3>
            <h3>Type: {profile.user?.type}</h3>
        </div>
    )
}