<script lang="ts">
    import {base} from "../utils/api";


    let email: String
    let password: Number
    let nickname: String
    
    let isLogin: boolean = true;
    const login = () => {
        const request = {"email": email, "password": password}
        base().post("/auth/login", JSON.stringify(request))
    }

    const signup = () => {
        const request = {"email": email, "password":password, "nickname":nickname}
    }

</script>

{#if isLogin}
<div>
    <input type="text" bind:value={email}/>
    <input type="password" bind:value={password}/>
</div>

<button on:click={login}>login</button>
<button on:click={() => isLogin = !isLogin}>signup</button>

{:else}
<div>
    <input type="text" bind:value={email}/>
    <input type="password" bind:value={password}/>
    <input type="text" bind:value={nickname}/>
</div>

<button on:click={login}>login</button>
<button on:click={signup}>signup</button>
{/if}
