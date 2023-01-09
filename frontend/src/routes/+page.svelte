<script>
    import {onMount} from "svelte";
    import API from "./api/api";

    let boards;

    onMount(async () => {
        try {
            boards = await API.get("/boards").then(res => res.data);
        } catch (e) {
            console.log(e)
        }
    })
</script>

{boards}