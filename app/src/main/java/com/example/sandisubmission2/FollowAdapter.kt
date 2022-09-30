import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sandisubmission2.ItemsUser
import com.example.sandisubmission2.databinding.ItemrowBinding

class FollowAdapter (private val listUser: ArrayList<ItemsUser>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemrowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (avatar, id, name) = listUser[position]
        holder.binding.username.text = name
        holder.binding.iduser.text = id.toString()
        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.binding.imageView)
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(var binding: ItemrowBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}