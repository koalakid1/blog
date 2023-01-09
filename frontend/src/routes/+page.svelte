<script lang="ts">
    import {boards} from './store.ts'
    import {base} from "./api/api";
    import type Board from "./api/types.svelte";

    let board: Board = {
        title: "",
        content: "",
        writer: "",
    };

    async function boardPost() {
        await base().post("boards", JSON.stringify(board))
        await boards.get()
    }
</script>

<ul>
    {#each $boards as board}
        <li>
            {board.title}
        </li>
    {/each}
</ul>

<input bind:value={board.title}/>
<input bind:value={board.content}/>
<input bind:value={board.writer}/>
<button type="button" on:click={boardPost}>등록</button>


