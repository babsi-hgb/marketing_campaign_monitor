package at.oberauer.crawlers.rss;

import at.oberauer.data.RSSChannel;
import at.oberauer.data.RSSChannelRepository;
import at.oberauer.data.RSSItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by michael on 12.09.17.
 */
@Component
@Configurable
public class RSSScheduler {
    final RSSChannelRepository repo;
    final RSSItemRepository irepo;
    final TaskExecutor exe;

    //Must be <= queue capacity of executor
    private int channels_per_cycle = 5;

    public int getChannels_per_cycle() {
        return channels_per_cycle;
    }

    public void setChannels_per_cycle(int channels_per_cycle) {
        this.channels_per_cycle = channels_per_cycle;
    }

    @Autowired
    public RSSScheduler(RSSChannelRepository repo, RSSItemRepository irepo, TaskExecutor exe) {
        this.repo = repo;
        this.exe = exe;
        this.irepo = irepo;
    }

    @Scheduled(fixedRate = 30000)
    public void runCrawler(){
        int num = 0;
        for(RSSChannel channel : repo.findAll(new Sort(Sort.Direction.ASC, "lastUpdate"))){
            exe.execute(new RSSChannelReader(channel, irepo));
            channel.setLastUpdate(new Date());
            repo.save(channel);
            ++num;
            if (num >= channels_per_cycle)
                break;
        }
    }
}