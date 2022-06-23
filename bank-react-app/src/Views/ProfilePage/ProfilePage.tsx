import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { Navbar } from "../../Components/Navbar/Navbar";
import { getUserDetails } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";

import "./ProfilePage.css";

export const ProfilePage:React.FC = () => {

    const profile = useSelector((state:RootState) => state.user);

    const dispatch: AppDispatch = useDispatch();

    const { username } = useParams();

    const navigator = useNavigate();

    const handleEdit = () => {
        navigator("/edit-profile");
    }

    useEffect(() => {
        if (!profile.user) {
            navigator("/login");
        }
    }, []);

    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>{profile.user?.firstname}'s Profile</h1>
            </div>
            <div className="info">
                <h3>First Name: {profile.user?.firstname}</h3>
                <h3>Last Name: {profile.user?.lastname}</h3>
                <h3>Email: {profile.user?.email}</h3>
                <h3>Username: {profile.user?.username}</h3>
                <h3>Type: {profile.user?.type}</h3>
            </div>
            <button className="edit-profile-button" onClick={handleEdit}>
                Edit Profile
            </button>
        </div>
    )
}