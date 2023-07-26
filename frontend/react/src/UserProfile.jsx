const UserProfile = ({name, age, gender, imageNumber, ...props}) => {
    gender = gender === 'MALE' ? 'men' : 'women'
    return (
        <div>
            <h4>{ name }</h4>
            <p>{ age }</p>

            <img src={`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`} alt="Owens"/>
            { props.children}
        </div>
    )
}

export default UserProfile;